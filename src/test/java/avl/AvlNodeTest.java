package avl;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 09/07/13 Time: 15:29
 */
public class AvlNodeTest {

  private AvlNode<Integer> node;

  @BeforeEach
  public void setUp() {
    node = new AvlNode<Integer>(5);
  }

  @AfterEach
  public void tearDown() {
    node = null;
  }

  @Nested
  @DisplayName("Getters and setters")
  class TestsForGettersAndSetters {

    @Test
    @DisplayName("getLeft and getRight return correct nodes")
    void given_NodeHasLeftHasLeftAndRight_When_CallingGet_Then_ReturnsCorrectNodes() {
      AvlNode<Integer> nodeLeft = new AvlNode<>(1);
      AvlNode<Integer> nodeRight = new AvlNode<>(10);

      node.setLeft(nodeLeft);
      node.setRight(nodeRight);

      assertSame(node.getLeft(), nodeLeft);
      assertSame(node.getRight(), nodeRight);
    }

    @Test
    @DisplayName("getParent returns correct nodes")
    void given_NodeHasParent_When_GettingParent_Then_ReturnsCorrectNode() {
      AvlNode<Integer> nodeParent = new AvlNode<>(1);

      node.setParent(nodeParent);

      assertSame(node.getParent(), nodeParent);
    }

    @Test
    @DisplayName("getItem returns correct item")
    void given_SettingNewNodeItem_When_GettingItem_Then_ReturnsNewItem() {
      node.setItem(25);

      assertEquals(node.getItem(), 25);
    }

    @Test
    @DisplayName("getClosestNode returns correct nodes")
    void given_NodeHasCloseNode_When_GettingClosestNode_Then_ReturnsCorrectNode() {
      AvlNode<Integer> anotherNode = new AvlNode<>(1);

      node.setClosestNode(anotherNode);

      assertSame(node.getClosestNode(), anotherNode);
    }


    @Test
    @DisplayName("getHeight returns correct height")
    public void given_SettingNewHeight_When_GettingHeight_ThenReturnsNewHeight() {
      int expectedHeight = 1000213;

      node.setHeight(expectedHeight);

      assertEquals(expectedHeight, node.getHeight());
    }

  }

  @Nested
  @DisplayName("Comparation methods")
  class TestsForComparationMethods {

    /*
    hasLeft(n) and hasRight(n) returns true or false
    when n has or does not have left and right node respectively
     */
    @Test
    @DisplayName("hasLeft and hasRight return true if there is left or right node")
    public void given_NodeHasNoLeftAndRight_When_SettingLeftAndRight_Then_ReturnsNewNodes() {
      assertFalse(node.hasLeft());
      assertFalse(node.hasRight());

      AvlNode<Integer> node2 = new AvlNode<>(6);
      node.setLeft(node2);
      assertTrue(node.hasLeft());

      AvlNode<Integer> node3 = new AvlNode<>(12);
      node.setRight(node3);
      assertTrue(node.hasRight());
    }

    /*
    hasParent(n) returns true or false when n has or does not have parent node
     */
    @Test
    @DisplayName("hasParent returns true if there is parent node")
    public void given_NodeHasNoParent_When_SettingParent_Then_ReturnsNewNode() {
      assertFalse(node.hasParent());

      AvlNode<Integer> node2 = new AvlNode<>(10);
      node.setParent(node2);

      assertTrue(node.hasParent());
    }

    /*
    A node is a leaf when it has no successors, meaning it has no left and right node,
    isLeaf(n) method returns true or false when n has or does not have left and right node
     */
    @Test
    @DisplayName("isLeaf returns true if there is no left and right node")
    public void given_LeafNodeHasNoLeftAndRight_When_SettingLeftOrRight_Then_IsNotLeaf() {
      assertTrue(node.isLeaf());

      AvlNode<Integer> node2 = new AvlNode<Integer>(6);
      node.setLeft(node2);

      assertFalse(node.isLeaf());

      AvlNode<Integer> node3 = new AvlNode<Integer>(12);
      node.setRight(node3);
      assertFalse(node.isLeaf());
    }

  }
}
