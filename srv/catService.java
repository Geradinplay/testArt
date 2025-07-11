// import java.util.List;

// import org.springframework.stereotype.Component;

// import com.sap.cds.reflect.CdsEntity;
// import com.sap.cds.services.handler.EventHandler;
// import com.sap.cds.services.handler.annotations.After;
// import com.sap.cds.services.handler.annotations.ServiceName;

// @Component
// @ServiceName("CatalogService")
// public class catService implements EventHandler{
    
//      @After(event = ReadEvent.class, entity = "CatalogService.Books")
//      public void afterReadBook(List<CdsEntity>books){
//         for(CdsEntity book: books){
//             Integer stock = book.getInteger("stock");
//             if (stock != null && stock > 111) {
//                 String title = book.getString("title");
//                 book.put("title", title + " -- 11% discount!");
//             }
//         }
//      }
// }
