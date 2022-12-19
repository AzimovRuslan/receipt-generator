# ClevertecTestTask

ИСПОЛЬЗУЕМЫЙ СТЕК ТЕХНОЛОГИЙ 
----------------------------
Spring Boot
Spring Data (JpaRepository)
Hibernate
Slf4j
REST
PostgreSQL
Gradle

ОПИСАНИЕ ЭНДПОИНТОВ
-------------------
/api/products/ [GET] - вывод всех хранящихся продуктов
/api/products/{id} [GET] - вывод продукта по айди
/api/products [POST] - добавление нового продукта
/api/products/{id} [DELETE] - удаление продукта по айди
/api/products/{id} [PUT] - обновление продукта по айди

/api/discount_cards/ [GET] - вывод всех хранящихся скидочных карт
/api/discount_cards/{id} [GET] - вывод скидочной карты по айди
/api/discount_cards [POST] - добавление новой скидочной карты
/api/discount_cards/{id} [DELETE] - удаление скидочной карты по айди
/api/discount_cards/{id} [PUT] - обновление скмдочной карты по айди

/api/receipts/ [GET] - вывод всех хранящихся чеков
/api/receipts/{id} [GET] - вывод чека по айди
/api/receipts [POST] - добавление нового чека
/api/receipts/{id} [DELETE] - удаление чека по айди
/api/receipts/{id} [PUT] - обновление чека по айди
