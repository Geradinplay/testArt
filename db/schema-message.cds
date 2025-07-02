// using {
//     Currency,
//     managed,
//     sap
// } from '@sap/cds/common';

namespace sap.capire.vectorTest;

entity Message {
    key ID   : UUID;
        content : String;
        vector : LargeBinary;
}