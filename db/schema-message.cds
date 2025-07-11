// using {
//     Currency,
//     managed,
//     sap
// } from '@sap/cds/common';

namespace vector.test;

entity Message {
    key ID   : UUID;
        content : String;
        embeddings : Association to many MessageEmbedding on embeddings.message_ID = ID;
}


entity MessageEmbedding {
  key ID         : UUID @Core.Computed;
  message_ID     : UUID;
  message        : Association to Message on message_ID = message.ID;
  idx            : Integer;
  value          : DecimalFloat;
}