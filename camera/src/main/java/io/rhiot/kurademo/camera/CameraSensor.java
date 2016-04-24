package io.rhiot.kurademo.camera;

import org.apache.camel.CamelContext;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.component.netty4.http.NettyHttpComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.language.simple.FileLanguage;
import org.eclipse.kura.camel.router.CamelRouter;

public class CameraSensor extends CamelRouter {

    @Override
    public void configure() throws Exception {
        from("file:///tmp/camera").
                to("netty4-http:http://things-service.net:8080/camera/process/cameraHall001/eu");
    }

    @Override
    protected void beforeStart(CamelContext camelContext) {
        super.beforeStart(camelContext);
        camelContext.addComponent("netty4-http", new NettyHttpComponent());
        registerLanguage("file", new FileLanguage());
        camelContext.addComponent("file", new FileComponent());
    }

    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new CameraSensor());
        camelContext.start();
        Thread.sleep(360 * 1000);
    }

}
