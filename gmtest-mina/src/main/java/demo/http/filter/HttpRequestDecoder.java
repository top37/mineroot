package demo.http.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class HttpRequestDecoder extends MessageDecoderAdapter {

	private static final byte[] CONTENT_LENGTH = new String("Content-Length:")
			.getBytes();

	private CharsetDecoder decoder;

	private HttpRequestMessage request = null;

	public static final String charset = "UTF-8";

	public CharsetDecoder getDecoder() {
		return decoder;
	}

	public void setEncoder(CharsetDecoder decoder) {
		this.decoder = decoder;
	}

	public HttpRequestMessage getRequest() {
		return request;
	}

	public void setRequest(HttpRequestMessage request) {
		this.request = request;
	}

	public void setDecoder(CharsetDecoder decoder) {
		this.decoder = decoder;
	}

	public HttpRequestDecoder() {
		decoder = Charset.forName(charset).newDecoder();
	}

	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		try {
			return messageComplete(in) ? MessageDecoderResult.OK
					: MessageDecoderResult.NEED_DATA;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return MessageDecoderResult.NOT_OK;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		HttpRequestMessage m = decodeBody(in);
		// Return NEED_DATA if the body is not fully read.
		if (m == null) {
			return MessageDecoderResult.NEED_DATA;
		}
		out.write(m);
		return MessageDecoderResult.OK;

	}

	/*
	 * 判断HTTP请求是否完整，若格式有错误直接抛出异常
	 */
	private boolean messageComplete(IoBuffer in) {
		int last = in.remaining() - 1;
		if (in.remaining() < 4) {
			return false;
		}
		// to speed up things we check if the Http request is a GET or POST
		if (in.get(0) == (byte) 'G' && in.get(1) == (byte) 'E'
				&& in.get(2) == (byte) 'T') {
			// Http GET request therefore the last 4 bytes should be 0x0D 0x0A
			// 0x0D 0x0A
			return in.get(last) == (byte) 0x0A
					&& in.get(last - 1) == (byte) 0x0D
					&& in.get(last - 2) == (byte) 0x0A
					&& in.get(last - 3) == (byte) 0x0D;
		} else if (in.get(0) == (byte) 'P' && in.get(1) == (byte) 'O'
				&& in.get(2) == (byte) 'S' && in.get(3) == (byte) 'T') {
			// Http POST request
			// first the position of the 0x0D 0x0A 0x0D 0x0A bytes
			int eoh = -1;
			for (int i = last; i > 2; i--) {
				if (in.get(i) == (byte) 0x0A && in.get(i - 1) == (byte) 0x0D
						&& in.get(i - 2) == (byte) 0x0A
						&& in.get(i - 3) == (byte) 0x0D) {
					eoh = i + 1;
					break;
				}
			}
			if (eoh == -1) {
				return false;
			}
			for (int i = 0; i < last; i++) {
				boolean found = false;
				for (int j = 0; j < CONTENT_LENGTH.length; j++) {
					if (in.get(i + j) != CONTENT_LENGTH[j]) {
						found = false;
						break;
					}
					found = true;
				}
				if (found) {
					// retrieve value from this position till next 0x0D 0x0A
					StringBuilder contentLength = new StringBuilder();
					for (int j = i + CONTENT_LENGTH.length; j < last; j++) {
						if (in.get(j) == 0x0D) {
							break;
						}
						contentLength.append(new String(
								new byte[] { in.get(j) }));
					}
					// if content-length worth of data has been received then
					// the message is complete
					return Integer.parseInt(contentLength.toString().trim())
							+ eoh == in.remaining();
				}
			}
		}

		// the message is not complete and we need more data
		return false;

	}

	private HttpRequestMessage decodeBody(IoBuffer in) {
		request = new HttpRequestMessage();
		try {
			request.setHeaders(parseRequest(new StringReader(in
					.getString(decoder))));
			return request;
		} catch (CharacterCodingException ex) {
			ex.printStackTrace();
		}

		return null;

	}

	private Map<String, String[]> parseRequest(StringReader is) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		BufferedReader rdr = new BufferedReader(is);

		try {
			// Get request URL.
			String line = rdr.readLine();
			String[] url = line.split(" +");
			if (url.length < 3) {
				return map;
			}

			map.put("URI", new String[] { line });
			map.put("Method", new String[] { url[0].toUpperCase() });
			map.put("Protocol", new String[] { url[2] });
			// Read header
			while ((line = rdr.readLine()) != null && line.length() > 0) {
				String[] tokens = line.split(": ");
				map.put(tokens[0], new String[] { tokens[1] });
			}

			// If method 'POST' then read Content-Length worth of data
			if (url[0].equalsIgnoreCase("POST")) {
				int len = Integer.parseInt(map.get("Content-Length")[0]);
				char[] buf = new char[len];
				int bufcount = rdr.read(buf);
				if (bufcount <= len) {
					line = String.copyValueOf(buf).substring(0, bufcount);
				}
			} else if (url[0].equalsIgnoreCase("GET")) {
				int idx = url[1].indexOf('?');
				if (idx != -1) {
					map.put("Context",
							new String[] { url[1].substring(1, idx) });
					line = url[1].substring(idx + 1);
				} else {
					line = null;
				}
			}
			if (line != null) {
				map.put("Context", new String[] { line });
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return map;
	}

}
