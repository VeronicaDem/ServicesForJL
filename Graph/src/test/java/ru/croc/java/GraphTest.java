package ru.croc.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.croc.graph.Atom;
import ru.croc.graph.DateNode;
import ru.croc.graph.Graph;
import ru.croc.graph.Node;

/**
 * Тест для графов
 */
public class GraphTest {

   @Test
   /**
    * Тестирование функции {@link Graph#addVertex} на пустом графе
    */
   public void testEmptyGraphOnAddVertex() {
      Graph emptyGraph = new Graph(new Node<Atom>(new Atom("", "", false), false,
              "emptyWord"));
      emptyGraph.addVertex(DateNode.month, "v1", false);
      emptyGraph.addVertex(DateNode.year, "v2", false);
      Assertions.assertTrue(emptyGraph.containsVertex("v1"));
      Assertions.assertTrue(emptyGraph.containsVertex("v2"));
   }
   @Test
   /**
    * Тестирование функции {@link Graph#deleteVertex(String)}
    */
   public void testEmptyGraphOnDeleteVertex() {
      Graph emptyGraph = new Graph(new Node<Atom>(new Atom("", "", false), false,
              "emptyWord"));
      emptyGraph.addVertex(DateNode.month, "v1", false);
      emptyGraph.addVertex(DateNode.year, "v2", false);
      emptyGraph.deleteVertex("v1");


      // проверка на удаление вершины
      Assertions.assertFalse(emptyGraph.containsVertex("v1"));
      // проверка на то, что функция не "сломала" другие вершины
      Assertions.assertTrue(emptyGraph.containsVertex("v2"));
   }
   /**
    * Тестирование функции {@link Graph#addEdge(String, String)}
    */
   @Test
   public void testEmptyGraphOnAddEdge() {
      Graph emptyGraph = new Graph(new Node<Atom>(new Atom("", "", false), false,
              "emptyWord"));
      emptyGraph.addVertex(DateNode.month, "v1", false);
      emptyGraph.addVertex(DateNode.year, "v2", false);
      // Вершины есть в графе
      String message = emptyGraph.addEdge("v1", "v2");
      Assertions.assertEquals("Успешно", message);
      Assertions.assertTrue(emptyGraph.containsEdge("v1", "v2"));
      // Вершин нет в графе
      message = emptyGraph.addEdge("v3", "v2");
      Assertions.assertEquals("Создайте узлы", message);
      Assertions.assertFalse(emptyGraph.containsEdge("v3", "v2"));

   }
   /**
    * Тестирование функции {@link Graph#deleteEdge(String, String)}
    */
   @Test
   public void testEmptyGraphOnDeleteEdge() {
      Graph emptyGraph = new Graph(new Node<Atom>(new Atom("", "", false), false,
              "emptyWord"));
      emptyGraph.addVertex(DateNode.month, "v1", false);
      emptyGraph.addVertex(DateNode.year, "v2", false);
      emptyGraph.addEdge("v1", "v2");
      emptyGraph.deleteEdge("v1", "v2");
      Assertions.assertFalse(emptyGraph.containsEdge("v1", "v2"));
   }
   /**
    * Тестирование функции {@link Graph#searchAndExec(String)} (String)}
    * Проверка на наличие
    */
   /*
   @Test
   public void testIsMemberAndExec() {
      Graph emptyGraph = new Graph(new Node<Atom>(new Atom("", "", false), false,
              "emptyWord"));
      // YYYY-MM-DD
      emptyGraph.addVertex(DateNode.year, "v1", true);
      emptyGraph.addVertex(DateNode.month, "v2", false);
      emptyGraph.addVertex(DateNode.day, "v3", false);
      emptyGraph.addVertex(new Atom("-", "-", false), "v4", false);
      emptyGraph.addEdge("v1", "v4");;
      emptyGraph.addEdge("v4", "v2");
      emptyGraph.addVertex(new Atom("-", "-", false), "v5", false);
      emptyGraph.addEdge("v2", "v5");
      emptyGraph.addEdge("v5", "v3");
      Assertions.assertTrue(emptyGraph.searchAndExec("2021-03-28"));
      Assertions.assertFalse(emptyGraph.searchAndExec("2021/03/28"));
      emptyGraph.addVertex(DateNode.month, "v6", false);
      emptyGraph.addEdge("v5", "v6");
      Assertions.assertTrue(emptyGraph.searchAndExec("2021-03-04"));
   }
   */

   /**
    *  Тестирование функции {@link Graph#searchAndExec(String)} (String)}
    *  Проверка на замену
    */
   @Test
   public void testIsMemberAndExecOnReplacing() {
      Graph emptyGraph = new Graph(new Node<Atom>(new Atom("", "", false), false,
              "emptyWord"));
      // YYYY-(MM)-DD -> YYYY-(mm)-DD
      emptyGraph.addVertex(DateNode.year, "v1", true);
      emptyGraph.addVertex(DateNode.monthReplace, "v2", false);

      emptyGraph.addVertex(DateNode.day, "v3", false);
      emptyGraph.addVertex(new Atom("-", "-", false), "v4", false);
      emptyGraph.addEdge("v1", "v4");;
      emptyGraph.addEdge("v4", "v2");
      emptyGraph.addVertex(new Atom("-", "-", false), "v5", false);
      emptyGraph.addEdge("v2", "v5");
      emptyGraph.addEdge("v5", "v3");

      Assertions.assertEquals("2021-Март-28", emptyGraph.searchAndExec("2021-03-28"));
   }
}
