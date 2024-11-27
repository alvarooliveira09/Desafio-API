# Desafio 2 - Agência de Viagens

## b) Instruções para execução (Windows 11):

1. Tenha instalado previamente os aplicativos Docker Desktop ([docker.com](https://www.docker.com/)) e o IntelliJ Community ([jetbrains.com/idea/download](https://www.jetbrains.com/idea/download/)).

2. Realize download dos arquivos deste repositório.
   
3. Docker container:
  a) Execute o seguinte comando para criar o Docker container:
```bash
docker run --name desafio2 -e POSTGRES_PASSWORD=SenhaTemporaria2025 -d -p 5432:5432 postgres
```
4. Abra o arquivo Desafio2Application e execute a classe main do projeto.

5. Acesse em um navegador o seguinte endereço: 
  ➜ http://localhost:8080/swagger-ui/index.html

6. Utilize o Swagger para visualizar e testar todos os endpoints da API.

## Screenshots
![App Screenshot](https://github.com/alvarooliveira09/Desafio-API/blob/main/Print%20Swagger.jpeg)
