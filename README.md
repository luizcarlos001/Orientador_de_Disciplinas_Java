# Orientador de Disciplinas – BICT / Engenharia da Computação

Aplicação desenvolvida em Java com interface gráfica (Swing) para auxiliar na organização das disciplinas do curso, permitindo cadastrar, alterar, excluir e listar disciplinas obrigatórias e optativas, além de calcular créditos e carga horária total.

---

## 🎯 Objetivo do Sistema

O sistema tem como objetivo facilitar o controle das disciplinas cursadas ou planejadas, permitindo:

- Cadastrar disciplinas com código, nome, créditos, semestre recomendado e tipo.
- Evitar códigos duplicados e códigos com tamanho inválido.
- Exibir todas as disciplinas em uma tabela, com categoria e carga horária.
- Calcular automaticamente a carga horária (créditos × 15).
- Salvar e carregar os dados de um arquivo texto, mantendo as informações entre execuções.

---

## ⚙️ Funcionalidades Implementadas

- Cadastro de disciplinas obrigatórias e optativas.
- Alteração dos dados de uma disciplina selecionada.
- Exclusão de disciplinas cadastradas.
- Busca por nome ou código.
- Filtro por categoria:
  - Obrigatórias do BICT.
  - Específicas da Engenharia da Computação.
- Exibição de resumo com:
  - Total de disciplinas.
  - Total de créditos cadastrados.
  - Carga horária obrigatória pendente.
  - Carga horária optativa pendente.
- Persistência dos dados em arquivo CSV (`dados_disciplinas.csv`).

---

## 🗂️ Estrutura do Projeto

src/
├─ model/
│ ├─ Disciplina.java
│ ├─ DisciplinaObrigatoria.java
│ └─ DisciplinaOptativa.java
│
├─ controller/
│ └─ GerenciadorDisciplinas.java
│
├─ view/
│ └─ TelaPrincipal.java
│
└─ Main.java

yaml
Copiar código

---

## 🧠 Conceitos de Programação Orientada a Objetos Utilizados

### Classes, Objetos e Encapsulamento
A classe abstrata `Disciplina` representa o conceito geral de uma disciplina, contendo atributos como código, nome, créditos e semestre recomendado.  
O acesso aos atributos é feito por meio de métodos públicos, seguindo o princípio do encapsulamento.

### Herança
A classe `Disciplina` é estendida pelas classes:
- `DisciplinaObrigatoria`
- `DisciplinaOptativa`

Cada subclasse especializa o comportamento conforme o tipo da disciplina.

### Polimorfismo
O sistema utiliza polimorfismo por meio de métodos abstratos definidos em `Disciplina` e implementados nas subclasses, como:
- `getTipo()`
- `getCategoria()`

Dessa forma, a interface e o controlador trabalham sempre com o tipo `Disciplina`, enquanto o comportamento varia conforme a subclasse instanciada.

### Collections
As disciplinas são armazenadas em uma coleção do tipo `ArrayList<Disciplina>`, permitindo operações de cadastro, remoção, listagem, busca e filtragem.

---

## 💾 Armazenamento Permanente

Os dados das disciplinas são salvos automaticamente em um arquivo CSV (`dados_disciplinas.csv`) sempre que uma disciplina é cadastrada, alterada ou excluída.  
Ao iniciar o sistema, o arquivo é lido e os dados são carregados novamente para a aplicação.

---

## 🖥️ Interface Gráfica

A interface gráfica foi desenvolvida utilizando o pacote `javax.swing`, com os seguintes componentes principais:

- `JFrame`
- `JTextField`
- `JButton`
- `JComboBox`
- `JTable`
- `JOptionPane`

A interação é feita de forma simples e direta, conforme os conteúdos trabalhados em sala de aula.

---

## ▶️ Como Compilar e Executar

1. Certifique-se de ter o **JDK 8 ou superior** instalado.
2. Organize o projeto conforme a estrutura apresentada.
3. No terminal, dentro da pasta `src`, compile:

```bash
javac model/*.java controller/*.java view/*.java Main.java
Execute a aplicação:

bash
Copiar código
java Main
O arquivo dados_disciplinas.csv será criado automaticamente após o primeiro cadastro.