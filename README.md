# Java Database Connectivity (JDBC) - Notas de Laboratório

Este repositório contém a implementação prática de fluxos de trabalho de conectividade de banco de dados utilizando o Core JDBC dentro do ecossistema Java, visando o controlo absoluto sobre o ciclo de vida de transações de baixo nível e a execução de SQL puro. Desenvolvido sob um ambiente Linux Ubuntu, o projeto mapeia componentes estruturais diretamente contra uma instância local do MySQL Server.

O objetivo principal deste módulo é dominar a mecânica operacional por trás da orquestração manual de conexões, envio de consultas, segurança na compilação de instruções, incorporação de parâmetros e gestão de atomicidade por meio do controlo de estado transacional ACID, eliminando a dependência de frameworks ORM de alto nível.

## 1. Pipeline Arquitetural e Framework de Componentes

O ciclo de vida programático segue uma sequência determinística e unidirecional, onde os dados transitam por três principais abstrações de interface geridas pelo pacote `java.sql`:

* **Connection:** A ponte subjacente ao nível de socket que conecta o espaço de memória da JVM à porta do daemon do banco de dados local (padrão 3306). Inicializada via `DriverManager` utilizando um mapeamento de parâmetros por propriedades externas.
* **Statement / PreparedStatement:** Compila e envia o payload SQL bruto através do túnel da conexão. A subclasse `PreparedStatement` fornece a pré-compilação da instrução no lado do banco de dados e atua como uma camada absoluta contra SQL Injection, tratando os marcadores de posição (`?`) estritamente como tokens de dados tipados, em vez de segmentos de texto executáveis.
* **ResultSet:** Um buffer de dados tabular virtual contínuo e de direção única que atua como um ponteiro de memória. A navegação através do cursor requer a avaliação iterativa do método `.next()` para deslocar os limites e ler os primitivos tipados.

## 2. Operações Estruturais Implementadas

### A. Recuperação de Dados (Operações de Leitura)
Utiliza a criação explícita de instruções para puxar linhas relacionais para instâncias locais. A iteração processa as variáveis sequencialmente, mantendo uma sincronização rigorosa dos tipos de dados.

```java
st = conn.createStatement();
rs = st.executeQuery("SELECT * FROM department");
while (rs.next()) {
    int id = rs.getInt("Id");
    String name = rs.getString("Name");
    System.out.println(id + ", " + name);
}
