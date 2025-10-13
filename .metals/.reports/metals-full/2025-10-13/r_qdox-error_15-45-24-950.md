error id: file://<WORKSPACE>/granary-backend/src/main/java/com/example/granary_backend/infrastructure/web/mpesa/MpesaCallbackController.java
file://<WORKSPACE>/granary-backend/src/main/java/com/example/granary_backend/infrastructure/web/mpesa/MpesaCallbackController.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[28,13]

error in qdox parser
file content:
```java
offset: 929
uri: file://<WORKSPACE>/granary-backend/src/main/java/com/example/granary_backend/infrastructure/web/mpesa/MpesaCallbackController.java
text:
```scala
package com.example.granary_backend.infrastructure.web.mpesa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.granary_backend.infrastructure.external.mpesa.dto.StkCallbackDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaCallbackController {

    private static final Logger log = LoggerFactory
            .getLogger(MpesaCallbackController.class);
    private final MpesaCallbackHandler callbackHandler;

    public MpesaCallbackController(MpesaCallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    log.info(@@"Received M-pesa Callback for ConversationID: {}",callbackDTO.result().conversationID());

    @RequestBody @Valid StkCallbackDTO callbackDTO)
    {
        log.info("Received M-pesa Callback for ConversionID: {}",
                callbackDTO.result().conversationID());

        try {
        } catch (Exception e) {
            log.error(
                    "Error processing M-Pesa callback for ConversationID: {}. Returning OK to M-Pesa to prevent retries. Details: {}",
                    callbackDTO.result().conversationID(), callbackDTO, e);

            return ResponseEntity.ok(MpesaCallbackResponse.success());
        }
        log.error("Error encountered during callback processing, returning OK to M-Pesa.", e);

        return ResponseEntity.ok(MpesaCallbackResponse.success());
    }
}

}

```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:489)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:587)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:584)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:619)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:617)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1306)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:584)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:916)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:687)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1095)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:619)
	java.base/java.lang.Thread.run(Thread.java:1447)
```
#### Short summary: 

QDox parse error in file://<WORKSPACE>/granary-backend/src/main/java/com/example/granary_backend/infrastructure/web/mpesa/MpesaCallbackController.java