
IMAGE_NAME=TutorMatch/default-image

# Переход в нужную директорию
pushd ../../

# Использование полного пути к mvnw
E:/KP6S/TutorService/mvnw spring-boot:build-image -DskipTests=true -Dspring-boot.build-image.imageName=$IMAGE_NAME

# Получение информации об образе
docker image ls | grep $IMAGE_NAME

# Возврат в исходную директорию
popd