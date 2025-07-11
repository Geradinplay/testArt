using { vector.test as db } from '../db/schema-message.cds';

service MessageService @(path : 'messages') {
    entity MessageEmbeddings as projection on db.MessageEmbedding;
    entity Messages as projection on db.Message {
        key ID,
        content,
        embeddings : Association to many db.MessageEmbedding on embeddings.message_ID = $projection.ID
    };
}