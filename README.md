# 🎙️ App Budgeting

Aplicação de controle financeiro por voz. O usuário envia um áudio descrevendo uma transação, e a IA transcreve, interpreta e registra automaticamente.

## Como funciona

```
Áudio -> Whisper (transcrição) -> Qwen 2.5 via Ollama (interpretação) -> MySQL (persistência) -> Resposta em áudio
```

1. O usuário envia um áudio dizendo, por exemplo: *"gastei 15 reais no mercado"*
2. O **Whisper** transcreve o áudio para texto
3. O **Qwen 2.5** interpreta o texto e extrai valor, categoria e descrição
4. A transação é salva no banco de dados
5. A aplicação responde com um áudio de confirmação via **Piper TTS**

## Tecnologias

- **Java 21** + Spring Boot
- **Spring AI** - integração com Whisper e Ollama
- **Ollama** - execução local do modelo Qwen 2.5 3B
- **Whisper** - transcrição de áudio para texto
- **Piper TTS** - síntese de voz para resposta em áudio
- **MySQL** - persistência das transações

## Pré-requisitos
- Java 21+
- Maven
- MySQL rodando na porta `3307`
- [Ollama](https://ollama.com) instalado e rodando localmente
- Modelo Qwen baixado: `ollama pull qwen2.5:3b`
- [Piper TTS](https://github.com/rhasspy/piper) instalado com modelo em português

## Configuração

Crie o arquivo `src/main/resources/application-local.properties` com suas configurações locais:

```properties
piper.executable=C:/piper/piper.exe
piper.model=C:/piper/models/pt_BR-faber-medium.onnx

spring.datasource.url=jdbc:mysql://localhost:3307/transaction
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Como rodar

```bash
# 1. Suba o Ollama com o modelo
ollama run qwen2.5:3b

# 2. Rode a aplicação com o profile local
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```