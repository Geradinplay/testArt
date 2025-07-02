using { sap.capire.vectorTest as db } from '../db/schema-message.cds';

service MessageService @(path : 'messages') {
    entity Messages as projection on db.Message;
}