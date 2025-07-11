// using { sap.capire.bookshop as my } from '../db/schema';
// service CatalogService @(path:'/browse') { //Сигнатура и путь доуступа

//   @readonly entity Books as select from my.Books {
//     *,//Вывести все
//     author.name as author //Это JOIN через author.
//   } excluding { createdBy, modifiedBy };//Исключаем поля createdBy и modifiedBy из проекции.

//   @requires: 'authenticated-user'
//   action submitOrder (book: Books:ID, quantity: Integer);
// }