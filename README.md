ScreenMatch-Spring-PostgreSQL
---
# Cenário
Projeto desenvolvido em Java com Spring Boot e JPA, que consome APIs públicas para buscar informações sobre séries de TV e seus episódios, armazenando os dados em um banco de dados relacional.

---

# Funcionalidades

-Buscar séries por título.

-Buscar episódios de uma série específica.

-Listar séries já buscadas, organizadas por gênero.

-Persistir séries e episódios no banco de dados usando JPA.

-Integração com API externa (OMDb API) para obter dados de séries e temporadas.

-Suporte a consultas complexas com JPQL, incluindo busca de episódios por trecho do título.

- Comentários nas classes explicando progresso e funcionalidades.
---

# Tecnologias utilizadas

Java 17+

Spring Boot

Spring Data JPA

Hibernate

Maven

Banco de dados relacional (H2, PostgreSQL ou MySQL)

OMDb API para consumo de dados de séries
