Написать простейший HTTP сервер, который будет принимать в качестве аргументов порт и директорию доступ до которой надо дать по
HTTP. Реализовать две команды:
• GET — получить ресурс
• HEAD — получить только заголовки от GET без самого ресурса
Если запрошенная директория содержит файл index.html сервер возвращает его, иначе
использует результат предыдущего задания для создания листинга директории.