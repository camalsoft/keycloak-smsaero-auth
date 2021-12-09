# Keycloak SMSAero authenticator
Основан на https://github.com/spiashko/keycloak-sms-authenticator

Модуль для подтверждения номера телефона при регистрации с использованием <a href="https://smsaero.ru/">SMSAero API v.1</a>

Введите логин, пароль и подпись:
* `/src/main/java/com/spiashko/keycloak/sms/producer/LogsSmsProducer.java`

Соберите модуль:
* `mvn package`

Скопируйте jar файл:
  * `$ cp target/keycloak-sms-authenticator-1.0.0-SNAPSHOT-jar-with-dependencies.jar _KEYCLOAK_HOME_/standalone/deployments/`


Authentication > Flows:
* Скопируйте поток "Browse" в поток "Browser with SMS".
* Нажмите «Actions» > «Add execution» в строке «Browser with SMS» и добавьте «SMS Authentication'». 
* Установите 'SMS Authentication' в 'REQUIRED'

Authentication > Bindings:
* Выберите «Browser with SMS» в качестве «Browser Flow» для REALM.

Убедитесь, что у всех пользователей есть атрибут `mobile_number`.

После того, как вы все сделаете, при входе в систему вам будет предложено ввести sms-код, который вы можете найти в журналах.
