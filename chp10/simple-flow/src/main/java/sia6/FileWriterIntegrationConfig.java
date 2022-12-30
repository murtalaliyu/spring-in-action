package sia6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.integration.file.dsl.Files;

import java.io.File;

@Configuration
//@ImportResource("classpath:/filewriter-config.xml")   // Java configuration is better than XML configuration
public class FileWriterIntegrationConfig {

    // This code demonstrates DSL (Domain Specific Language)
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))  // Inbound channel
                .<String, String>transform(t -> t.toUpperCase())    // Declares a transformer
                .channel(MessageChannels.direct("FileWriterChannel"))   // Optional: channel that connects the transformer to the outbound channel adapter
                .handle(Files   // Handles writing to a file
                        .outboundAdapter(new File("/tmp/sia6/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }

    // -----------------------------------------------------------------------
    // The code below demonstrates Java configuration (no DSL)

    // Declares a transformer
//    @Bean
//    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
//    public GenericTransformer<String, String> upperCaseTransformer() {
//        return text -> text.toUpperCase();
//    }
//
//    // Declares a file writer
//    @Bean
//    @ServiceActivator(inputChannel = "fileWriterChannel")
//    public FileWritingMessageHandler fileWriter() {
//        FileWritingMessageHandler handler =
//                new FileWritingMessageHandler(new File("/tmp/sia6/files"));
//        handler.setExpectReply(false);
//        handler.setFileExistsMode(FileExistsMode.APPEND);
//        handler.setAppendNewLine(true);
//        return handler;
//    }

}
