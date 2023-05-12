package avl;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;


/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */
public class AvlTreeTest {

  AvlTree<Integer> avlTree;
  Comparator<?> comparator;

  @BeforeEach
  public void setUp() {
    comparator = Comparator.comparingInt((Integer o) -> o);
    avlTree = new AvlTree(comparator);
  }

  @AfterEach
  public void tearDown() {
    avlTree = null;
    comparator = null;
  }



  @Nested
  @DisplayName("Test cases for delete an item or node")
  class deleteTestCases{

    @Test
    @DisplayName("Test delete empty avlTree")
    public void GIVEN_emptyAvlTree_WHEN_delete_THEN_nothing(){
      String expected = avlTree.toString();

      avlTree.delete(1);

      assertEquals(expected, avlTree.toString());
    }

    @Test
    @DisplayName("Test delete by item")
    public void GIVEN_notEmptyAvlTree_WHEN_delete_THEN_deleteCorrectlyNode(){
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);
      AvlNode<Integer> node3 = new AvlNode<Integer>(6);

      String expected = " | 5 | 4";

      avlTree.insertAvlNode(node1);
      avlTree.insertAvlNode(node2);
      avlTree.insertAvlNode(node3);
      avlTree.delete(6);

      assertEquals(expected, avlTree.toString());
    }

    @Test
    @DisplayName("Test delete all leafNodes")
    public void GIVEN_node_WHEN_deleteLeafNode_THEN_deleteCorrectlyLeafNode(){
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);
      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);
      node3 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node3);
      node4 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node4);
      node5 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node5);

      String tree = " | 7 | 2 | 3 | 9 | 8";
      assertEquals(tree, avlTree.toString());

      avlTree.delete(3); // right leaf node
      assertEquals(null, node3.getRight());
      assertEquals(0, node3.getHeight());
      assertEquals(2, avlTree.getTop().getHeight());
      assertEquals(" | 7 | 2 | 9 | 8", avlTree.toString());

      avlTree.delete(8); // left leaf node
      assertEquals( null, node2.getLeft());
      assertEquals( 0, node2.getHeight());
      assertEquals( 1, avlTree.getTop().getHeight());
      assertEquals( " | 7 | 2 | 9", avlTree.toString());

      avlTree.delete(2); // left leaf node
      assertEquals( null, node1.getLeft());
      assertEquals( 1, node1.getHeight());
      assertEquals( " | 7 | 9", avlTree.toString());

      avlTree.delete(9); // right leaf node
      assertEquals( null, node1.getRight());
      assertEquals( 0, node1.getHeight());
      assertEquals( " | 7", avlTree.toString());

      avlTree.delete(7); // left leaf node
      assertEquals( null, avlTree.getTop());
      assertEquals( "", avlTree.toString());

    }

    @Test
    @DisplayName("Test delete one leafNodes")
    public void GIVEN_nodeWithOneLeafs_WHEN_deleteLeafNode_THEN_deleteCorrectlyLeafNode() throws Exception {
      AvlNode<Integer> node1, node2, node3, node4, node5;

      node1 = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node1);

      node2 = new AvlNode<Integer>(9);
      avlTree.insertAvlNode(node2);

      node3 = new AvlNode<Integer>(2);
      avlTree.insertAvlNode(node3);

      node4 = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node4);

      node5 = new AvlNode<Integer>(3);
      avlTree.insertAvlNode(node5);

      String tree = " | 7 | 2 | 3 | 9 | 8";
      assertEquals(tree, avlTree.toString());

      avlTree.delete(2);
      assertEquals(node3.getItem(), node1.getLeft().getItem());
      assertEquals(null, node3.getRight());
      assertEquals(0, node3.getHeight());
      assertEquals(2, avlTree.getTop().getHeight());
      assertEquals(" | 7 | 3 | 9 | 8", avlTree.toString());

      avlTree.delete(9);
      assertEquals(node2.getItem(), node1.getRight().getItem());
      assertEquals(null, node2.getLeft());
      assertEquals(0, node2.getHeight());
      assertEquals(1, avlTree.getTop().getHeight());
      assertEquals(" | 7 | 3 | 8", avlTree.toString());
    }

    @Test
    @DisplayName("Test delete two leafNodes")
    public void GIVEN_nodeWithTwoLeafs_WHEN_deleteLeafNode_THEN_deleteCorrectlyLeafNodes() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
      assertEquals(expected, avlTree.toString());

      avlTree.delete(12);
      node = avlTree.search(8);
      assertEquals(14, (int) node.getRight().getItem());
      assertEquals(" | 20 | 8 | 4 | 14 | 10 | 22 | 24",
              avlTree.toString());

      avlTree.delete(8);
      assertEquals(10, (int) avlTree.getTop().getLeft().getItem());
      assertEquals(" | 20 | 10 | 4 | 14 | 22 | 24",
              avlTree.toString());
    }

    @Test
    @DisplayName("Test delete a node and rebalance the tree")
    public void GIVEN_node_WHEN_delete_THEN_deleteAndRebalanceCorrectly() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      assertEquals(3, avlTree.getTop().getHeight());

      avlTree.delete(22);
      assertEquals(12, (int) avlTree.getTop().getItem());
      assertEquals(avlTree.search(8), avlTree.getTop().getLeft());
      assertEquals(avlTree.search(20), avlTree.getTop().getRight());
    }

    @Test
    @DisplayName("Test delete top node")
    public void GIVEN_node_WHEN_delete_THEN_deleteTopNodeCorrectly() throws Exception {
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      assertEquals(3, avlTree.getTop().getHeight());

      avlTree.delete(20);
      assertEquals(" | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
    }
  }

  @Nested
  @DisplayName("Test cases for findSuccessor()")
  class findSuccessorTestCases{
    @Test
    public void testFindSuccessor(){
      AvlNode<Integer> node;

      node = new AvlNode<Integer>(20);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(8);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(22);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(12);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(24);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(10);
      avlTree.insertAvlNode(node);
      node = new AvlNode<Integer>(14);
      avlTree.insertAvlNode(node);

      node = avlTree.search(8);
      assertEquals(avlTree.search(10), avlTree.findSuccessor(node));
      node = avlTree.search(10);
      assertEquals(avlTree.search(12), avlTree.findSuccessor(node));
      node = avlTree.search(14);
      assertEquals(avlTree.search(20), avlTree.findSuccessor(node));

      String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
      assertEquals("testSearchNode", tree, avlTree.toString());
    }
  }

  @Nested
  @DisplayName("Test searchClosestNode")
  public class test_searchClosestNode {

    @Test
    @DisplayName("Test to find the closest node of the node passed as argument is the left")
    public void GIVEN_Node_WHEN_TheClosestNodeIsInTheLeft_Then_ReturnNegative1() throws Exception {
      int result;
      AvlNode<Integer> node = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      result = avlTree.searchClosestNode(node);
      assertEquals( -1, result, "testSearchClosestNode");
      avlTree.insertAvlNode(node);


      String tree = " | 7 | 4";
      assertEquals( tree, avlTree.toString(),"testSearchClosestNode");
    }
    @Test
    @DisplayName("Test to delete a Node that is contained in the tree")
    public void GIVEN_Node_WHEN_TheClosestNodeIsInTheRight_Then_Return1() throws Exception {
      int result;
      AvlNode<Integer> node = new AvlNode<Integer>(7);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(4);
      avlTree.insertAvlNode(node);

      node = new AvlNode<Integer>(9);
      result = avlTree.searchClosestNode(node);
      assertEquals( 1, result, "testSearchClosestNode");
      avlTree.insertAvlNode(node);

      String tree = " | 7 | 4 | 9";
      assertEquals( tree, avlTree.toString(),"testSearchClosestNode");
    }

    @Test
    @DisplayName("Test to delete a LeafNode that is contained in the tree")
    public void GIVEN_Node_WHEN_TheClosestNodeIsInTheRight_Then_Return0() throws Exception{
      int result;
      AvlNode<Integer> node = new AvlNode<Integer>(7);
      result = avlTree.searchClosestNode(node);
      assertEquals( 0, result, "testSearchClosestNode");
      avlTree.insertAvlNode(node);

      String tree = " | 7";
      assertEquals( tree, avlTree.toString(),"testSearchClosestNode");

    }
  }

  @Nested
  @DisplayName("Test cases for compareNodes")
  class compareNodeTestCases{

    @Test
    @DisplayName("Test compareNodes lower")
    public void GIVEN_twoDifferentAvlNodes_WHEN_compareNodes_THEN_negativeOne(){
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);

      assertEquals (-1, avlTree.compareNodes(node1, node2));
    }
    @Test
    @DisplayName("Test compareNodes equals")
    public void GIVEN_twoEqualsAvlNodes_WHEN_compareNodes_THEN_zero(){
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(4);

      assertEquals (0, avlTree.compareNodes(node1, node2));
    }
    @Test
    @DisplayName("Test compareNodes lower")
    public void GIVEN_twoDifferentAvlNodes_WHEN_compareNodes_THEN_one(){
      AvlNode<Integer> node1 = new AvlNode<Integer>(4);
      AvlNode<Integer> node2 = new AvlNode<Integer>(5);

      assertEquals (1, avlTree.compareNodes(node2, node1));
    }
  }

  @Test
  public void GIVEN_emptyAvlTree_WHEN_avlIsEmpty_THEN_true() {
    assertTrue(avlTree.avlIsEmpty(), "TestAvlIsEmpty");

    avlTree.insertTop(new AvlNode(5));
    assertFalse(avlTree.avlIsEmpty(), "TestAvlIsEmpty");
  }
  @Test
  public void GIVEN_avlTreeAndAvlNode_WHEN_insertTop_THEN_success() {
    AvlNode<Integer> node = new AvlNode(4);
    avlTree.insertTop(node);
    assertEquals(node, avlTree.getTop(), "TestInsertTop");
    String tree = " | 4";
    assertEquals( tree, avlTree.toString(), "TestInsertTop");
  }

  /*
  @Test
  public void testInsertingTheFirstElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6) ;
    avlTree_.insertAvlNode(node);
    assertEquals("testInsertingTheFirstElement", node, avlTree_.getTop());
  }
  */
  @Test
  public void GIVEN_avlTreeAndAvlNodes_WHEN_insert_THEN_success() {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
    AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);

    assertEquals( -1, avlTree.searchClosestNode(nodeLeft), "testInsertingSecondSmallerElement");
    assertEquals(node, nodeLeft.getClosestNode(), "testInsertingSecondSmallerElement");
    assertEquals(+1, avlTree.searchClosestNode(nodeRight));
    assertEquals(node, nodeRight.getClosestNode());
    assertEquals(0, avlTree.searchClosestNode(node));

    node.setLeft(nodeLeft);
    node.setRight(nodeRight);
    AvlNode<Integer> nodeRightLeft = new AvlNode<Integer>(7);
    avlTree.searchClosestNode(nodeRightLeft);
    assertEquals(-1, avlTree.searchClosestNode(nodeRightLeft));
    assertEquals(nodeRight, nodeRightLeft.getClosestNode());

    AvlNode<Integer> nodeLeftRight = new AvlNode<Integer>(5);
    assertEquals(1, avlTree.searchClosestNode(nodeLeftRight));
    assertEquals(nodeLeft, nodeLeftRight.getClosestNode());

    String tree = " | 6 | 4 | 9";
    assertEquals(tree, avlTree.toString());
  }
  @Test
  public void GIVEN_avlTreeAndAvlNodes_WHEN_firstInsert_THEN_nodeInsertedToLeft() {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(nodeLeft);

    assertEquals(node, nodeLeft.getParent());
    assertEquals(nodeLeft, node.getLeft());

    String tree = " | 6 | 4";
    assertEquals(tree, avlTree.toString());
  }
  @Test
  public void testSearchClosestNode() {
    int result;
    AvlNode<Integer> node = new AvlNode<Integer>(7);
    result = avlTree.searchClosestNode(node);
    assertEquals(0, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    result = avlTree.searchClosestNode(node);
    assertEquals(-1, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(9);
    result = avlTree.searchClosestNode(node);
    assertEquals(1, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(6);
    result = avlTree.searchClosestNode(node);
    assertEquals(1, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    result = avlTree.searchClosestNode(node);
    assertEquals(-1, result);
    avlTree.insertAvlNode(node);

    String tree = " | 7 | 4 | 6 | 9 | 8";
    assertEquals(tree, avlTree.toString());
  }
  @Test
  public void testInsertingRightElement() {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(nodeRight);

    assertEquals(node, nodeRight.getParent());
    assertEquals(nodeRight, node.getRight());

    String tree = " | 6 | 9";
    assertEquals("testInsertingRightElement", tree, avlTree.toString());
  }
  /**
   * Test adding 7 - 4 - 9 - 3 - 5
   *
   * @throws Exception
   */
  @Test
  public void testHeightAndBalanceOfASimpleBalancedTree() {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals(0, node1.getHeight());
    assertEquals(0, avlTree.getBalance(node1));

    node2 = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node2);
    assertEquals(0, node2.getHeight());
    assertEquals(1, node1.getHeight());
    assertEquals(-1, avlTree.getBalance(node1));
    assertEquals(0, avlTree.getBalance(node2));

    node3 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node3);
    assertEquals(0, node3.getHeight());
    assertEquals(1, node1.getHeight());
    assertEquals(0, avlTree.getBalance(node1));
    assertEquals(0, avlTree.getBalance(node3));

    node4 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node4);
    assertEquals(0, node4.getHeight());
    assertEquals(1, node2.getHeight());
    assertEquals(2, node1.getHeight());
    assertEquals(-1, avlTree.getBalance(node2));
    assertEquals(-1, avlTree.getBalance(node1));
    assertEquals(0, avlTree.getBalance(node4));

    node5 = new AvlNode<Integer>(5);
    avlTree.insertAvlNode(node5);
    assertEquals(0, node5.getHeight());
    assertEquals(1, node2.getHeight());
    assertEquals(2, node1.getHeight());
    assertEquals(0, avlTree.getBalance(node2));
    assertEquals(-1, avlTree.getBalance(node1));
    assertEquals(0, avlTree.getBalance(node5));

    String tree = " | 7 | 4 | 3 | 5 | 9";
    assertEquals(tree, avlTree.toString());
  }
  /**
   * Testing adding 7 - 4 - 3
   *
   * @throws Exception
   */
  @Test
  public void testInsertingLeftLeftNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals(0, node1.getHeight());
    assertEquals(0, avlTree.getBalance(node1));

    node2 = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node2);
    assertEquals(0, node2.getHeight());
    assertEquals(1, node1.getHeight());
    assertEquals(-1, avlTree.getBalance(node1));
    assertEquals(0, avlTree.getBalance(node2));

    node3 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node3);
    assertEquals(node2, avlTree.getTop());
    assertEquals(node3, node2.getLeft());
    assertEquals(node1, node2.getRight());

    assertEquals(1, avlTree.getTop().getHeight());
    assertEquals(0, avlTree.getTop().getLeft().getHeight());
    assertEquals(0, avlTree.getTop().getRight().getHeight());
    assertEquals(-1, avlTree.height(node1.getLeft()));
    assertEquals(-1, avlTree.height(node1.getRight()));
    assertEquals(-1, avlTree.height(node3.getLeft()));
    assertEquals(-1, avlTree.height(node3.getRight()));

    String tree = " | 4 | 3 | 7";
    assertEquals(tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 10 - 14
   *
   * @throws Exception
   */
  @Test
  public void testInsertingRightRightNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals(0, node1.getHeight());
    assertEquals(0, avlTree.getBalance(node1));

    node2 = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node2);
    assertEquals(0, node2.getHeight());
    assertEquals(1, node1.getHeight());
    assertEquals(1, avlTree.getBalance(node1));
    assertEquals(0, avlTree.getBalance(node2));

    node3 = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node3);
    assertEquals(node2, avlTree.getTop());
    assertEquals(node1, node2.getLeft());
    assertEquals(node3, node2.getRight());

    assertEquals(1, avlTree.getTop().getHeight());
    assertEquals(0, avlTree.getTop().getLeft().getHeight());
    assertEquals(0, avlTree.getTop().getRight().getHeight());
    assertEquals(-1, avlTree.height(node1.getLeft()));
    assertEquals(-1, avlTree.height(node1.getRight()));
    assertEquals(-1, avlTree.height(node3.getLeft()));
    assertEquals(-1, avlTree.height(node3.getRight()));

    String tree = " | 10 | 7 | 14";
    assertEquals(tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 4 - 3 - 2 - 1
   *
   * @throws Exception
   */
  @Test
  public void testInserting7_4_3_2_1() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    node2 = new AvlNode<Integer>(4);
    node3 = new AvlNode<Integer>(3);
    node4 = new AvlNode<Integer>(2);
    node5 = new AvlNode<Integer>(1);

    avlTree.insertAvlNode(node1);
    avlTree.insertAvlNode(node2);
    avlTree.insertAvlNode(node3);
    avlTree.insertAvlNode(node4);
    avlTree.insertAvlNode(node5);

    assertEquals(node2, avlTree.getTop());
    assertEquals(node4, node2.getLeft());
    assertEquals(node1, node2.getRight());
    assertEquals(node5, node4.getLeft());
    assertEquals(node3, node4.getRight());
    assertEquals(0, node1.getHeight());
    assertEquals(2, node2.getHeight());
    assertEquals(1, node4.getHeight());

    String tree = " | 4 | 2 | 1 | 3 | 7";
    assertEquals(tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 4 - 3 - 2 - 1
   *
   * @throws Exception
   */
  @Test
  public void testInserting7_8_9_10_11() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    node2 = new AvlNode<Integer>(8);
    node3 = new AvlNode<Integer>(9);
    node4 = new AvlNode<Integer>(10);
    node5 = new AvlNode<Integer>(11);

    avlTree.insertAvlNode(node1);
    avlTree.insertAvlNode(node2);
    avlTree.insertAvlNode(node3);
    avlTree.insertAvlNode(node4);
    avlTree.insertAvlNode(node5);

    assertEquals(node2, avlTree.getTop());
    assertEquals(node4, node2.getRight());
    assertEquals(node1, node2.getLeft());
    assertEquals(node5, node4.getRight());
    assertEquals(node3, node4.getLeft());
    assertEquals(2, avlTree.getTop().getHeight());
    assertEquals(1, node4.getHeight());
    assertEquals(0, node1.getHeight());

    String tree = " | 8 | 7 | 10 | 9 | 11";
    assertEquals("testInserting7_8_9_10_11", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 2 - 3
   *
   * @throws Exception
   */
  @Test
  public void testInsertingLeftRightNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node3);

    assertEquals(node3, avlTree.getTop());
    assertEquals(node2, node3.getLeft());
    assertEquals(node1, node3.getRight());

    assertEquals(1, avlTree.getTop().getHeight());
    assertEquals(0, avlTree.getTop().getLeft().getHeight());
    assertEquals(0, avlTree.getTop().getRight().getHeight());
    assertEquals(-1, avlTree.height(node2.getLeft()));
    assertEquals(-1, avlTree.height(node2.getRight()));
    assertEquals(-1, avlTree.height(node1.getLeft()));
    assertEquals(-1, avlTree.height(node1.getRight()));

    String tree = " | 3 | 2 | 7";
    assertEquals(tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 9 - 8
   *
   * @throws Exception
   */
  @Test
  public void testInsertingRightLeftNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node3);

    assertEquals(node3, avlTree.getTop());
    assertEquals(node1, node3.getLeft());
    assertEquals(node2, node3.getRight());

    assertEquals(1, avlTree.getTop().getHeight());
    assertEquals(0, avlTree.getTop().getLeft().getHeight());
    assertEquals(0, avlTree.getTop().getRight().getHeight());
    assertEquals(-1, avlTree.height(node2.getLeft()));
    assertEquals(-1, avlTree.height(node2.getRight()));
    assertEquals(-1, avlTree.height(node1.getLeft()));
    assertEquals(-1, avlTree.height(node1.getRight()));

    String tree = " | 8 | 7 | 9";
    assertEquals(tree, avlTree.toString());
  }

  @Test
  public void testSearchNode() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    assertEquals(node1, avlTree.search(7));
    assertEquals(node2, avlTree.search(9));
    assertEquals(node3, avlTree.search(8));
    assertEquals((Integer) 2, avlTree.searchNode(new AvlNode<Integer>(2)).getItem());
    assertEquals(node4, avlTree.search(2));
    assertEquals(node5, avlTree.search(3));
    assertNull(avlTree.search(14));
    assertNull(avlTree.search(0));

    String tree = " | 8 | 3 | 2 | 7 | 9";
    assertEquals(tree, avlTree.toString());
  }


  @Nested
  @DisplayName("Tests for rebalance()")
  class rebalanceTestCases{

    @Test
    @DisplayName("Rebalance an unbalanced node")
    void GIVEN_notBalancedAVLTree_WHEN_rebalance_THEN_balancedTreeNode(){
      avlTree.insert(3);
      avlTree.insert(2);
      avlTree.insert(1);
      avlTree.insert(4);
      avlTree.insert(5);

      avlTree.rebalance(avlTree.getTop());

      assertEquals(1, avlTree.getBalance(avlTree.getTop()));
    }

    @Test
    @DisplayName("Rebalance an unbalanced node when node.getParent().getLeft() == node")
    void GIVEN_notBalancedAVLTree_WHEN_rebalanceLeftParentNode_THEN_balancedTreeNode(){
      avlTree.insert(5);
      avlTree.insert(3);
      avlTree.insert(7);
      avlTree.insert(2);
      avlTree.insert(4);
      avlTree.insert(6);
      avlTree.insert(8);

      AvlNode<Integer> unbalancedNode = avlTree.search(3);
      AvlNode<Integer> parentNode = unbalancedNode.getParent();
      parentNode.setLeft(unbalancedNode);

      avlTree.leftRotation(unbalancedNode);

      assertEquals(1, avlTree.getBalance(unbalancedNode));
    }


    @Test
    @DisplayName("Rebalance an unbalanced node with double left rotation")
    void GIVEN_notBalancedAVLTree_WHEN_rebalance_THEN_balancedTreeNodeWithDoubleLeftRotation(){
      avlTree.insert(3);
      avlTree.insert(1);
      avlTree.insert(2);

      avlTree.rebalance(avlTree.getTop());
      assertEquals(2, avlTree.getTop().getItem());
      assertEquals(1, avlTree.getTop().getLeft().getItem());
      assertEquals(3, avlTree.getTop().getRight().getItem());
    }



    @Test
    @DisplayName("Rebalance an unbalanced node with double right rotation")
    void GIVEN_notBalancedAVLTree_WHEN_rebalance_THEN_balancedTreeNodeWithDoubleRightRotation(){
      avlTree.insert(1);
      avlTree.insert(3);
      avlTree.insert(2);

      avlTree.rebalance(avlTree.getTop());
      assertEquals(2, avlTree.getTop().getItem());
      assertEquals(1, avlTree.getTop().getLeft().getItem());
      assertEquals(3, avlTree.getTop().getRight().getItem());
    }
  }

}
