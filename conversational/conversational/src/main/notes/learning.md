# why we choose JDC over JPA for AI Chat Memory
When Building an AI Chat Application using Spring AI and PostgreSql, we choose spring-boot-starter JDBC over spring-boot-starter JPA because
## 1. Native Alignment with Spring AI
Spring AI internally relies on JDC Template for it's built in database solutions(Like the deafault JdbcChatMemory). By sticking to JDBC, we follow the framework's native approach without introducing unnecessary heavy dependencies

## 2. Zero ORM Magic (Simplicity)
JPA/Hibernate is designed for the complex object-relational mapping, bringing features like Entity-state-Management, lazy loading and first-level caching.
Chat Memory is a simple append only system (we just insert new messages and fetch old ones by conversation_id). JDBC keeps this workflow lean and prevents over-engineering.

## 3. Performance and Raw SQL Control
LLM calls already introduce latency, JDBC is lighter and faster then JPA. It allows us to write highly optimized, raw SQL queries giving us exact control over daatabase execution without the overhead of JPQL translation.

## 4. Future proof for RAG
This is the biggest factor. IN future building a RAG requires storing AI embeddings using vector search. PostgreSQL handles this via pgvector extension, Which uses custom Mathematical operators. JPA struggles heavily with non-standard vector operations where as JDBC can execute raw queries flawlessly.