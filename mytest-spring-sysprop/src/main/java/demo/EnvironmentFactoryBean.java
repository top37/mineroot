package demo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import java.util.Properties;

public class EnvironmentFactoryBean extends PropertiesLoaderSupport implements FactoryBean<Properties> {

    public EnvironmentFactoryBean() {
    }

    public Properties getObject() throws Exception {
        Properties props = new Properties();
        this.loadProperties(props);
        props.putAll(System.getProperties());
        return props;
    }

    public Class<?> getObjectType() {
        return Properties.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
