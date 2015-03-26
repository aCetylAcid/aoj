import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
  public static void main (String[] args) throws Exception {
    BufferedReader br;
    br = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      Operation operation = Utils.readOperation(br);

      if (operation != null) {
        Tree tree1 = operation.getTree1();
        Tree tree2 = operation.getTree2();
        Operation.OperationType operationType =
                           operation.getOperationType();

        switch (operationType) {
          case INTERSECTION:
            System.out.println(tree1.intersection(tree2).toString());
            break;
          case UNION:
            System.out.println(tree1.union(tree2).toString());
            break;
        }
      } else {
        return;
      }
    }
  }
}



class Operation
{
  private Tree tree1;
  private Tree tree2;
  private OperationType operationType;

  public Operation () {
    this.tree1 = null;
    this.tree2 = null;
    this.operationType = OperationType.NONE;
  }

  public Operation (Tree tree1, Tree tree2,
                    OperationType operationType) {
    this.tree1 = tree1;
    this.tree2 = tree2;
    this.operationType = operationType;
  }

  public Tree getTree1 () {
    return tree1;
  }

  public Tree getTree2 () {
    return tree2;
  }

  public OperationType getOperationType () {
    return operationType;
  }

  public void setTree1 (Tree tree1) {
    this.tree1 = tree1;
  }

  public void setTree2 (Tree tree2) {
    this.tree2 = tree2;
  }

  public void setOperationType (OperationType operationType) {
    this.operationType = operationType;
  }

  /**
  * Define operation types.
  */
  public enum OperationType
  {
    NONE(0),
    INTERSECTION(1),
    UNION(2);

    private final int id;

    private OperationType (final int id) {
      this.id = id;
    }

    public int getId () {
      return id;
    }
  }
}

class Utils
{
  public static Operation readOperation (BufferedReader br) {
    Operation operation = new Operation();

    String line;
    try {
      if ((line = br.readLine()) == null) {
        return null;
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }

    if (line.split(" ").length != 3) {
      throw new IllegalArgumentException();
    }

    String[] words = line.split(" ");

    if (words[0].equals("i")) {
      operation.setOperationType(
                Operation.OperationType.INTERSECTION);
    }
    if (words[0].equals("u")) {
      operation.setOperationType(
                Operation.OperationType.UNION);
    }
    operation.setTree1(new Tree(words[1]));
    operation.setTree2(new Tree(words[2]));

    return operation;
  }
}

class Tree
{
  private Node root;

  public Tree () {
    this.root = new Node();
  }

  public Tree (Node root) {
    this.root = root;
  }

  public Tree (String treeStr) {
    Node rootNode = new Node(treeStr);
    this.root = rootNode;
  }

  public Node getRoot () {
    return root;
  }

  public void setRoot (Node rootNode) {
    this.root = rootNode;
  }

  public Tree intersection (Tree otherTree) {
    return new Tree(this.root.intersection(otherTree.root));
  }

  public Tree union (Tree otherTree) {
    return new Tree(this.root.union(otherTree.root));
  }

  public String toString() {
    return root.toString();
  }
}

class Node
{
  private Node rNode;
  private Node lNode;

  public Node () {
    this.rNode = null;
    this.lNode = null;
  }

  public Node (Node rNode, Node lNode) {
    this.rNode = rNode;
    this.lNode = lNode;
  }

  public Node (String nodeString) {
    char[] chars = nodeString.toCharArray();
    int level = 0;
    for (int i=0; i<chars.length; i++) {
      if (level == 1 && chars[i] == ',') {

        if (i-1 - 1 <= 0) { // if left node is empty
          this.setLNode(null);
        } else {
          String leftNodeString = nodeString.substring(1, i);
          this.setLNode(new Node(leftNodeString));
        }

        if (nodeString.length()-3 - (i-1) <= 0) {
          this.setRNode(null);
        } else {
          String rightNodeString = nodeString
                            .substring(i+1, nodeString.length() - 1);
          this.setRNode(new Node(rightNodeString));
        }

      } else if (chars[i] == '(') {
        level++;
      } else if (chars[i] == ')' ) {
        level--;
      }
    }

    return;
  }

  public Node getRNode () {
    return rNode;
  }

  public Node getLNode () {
    return lNode;
  }

  public void setRNode (Node rNode) {
    this.rNode = rNode;
  }

  public void setLNode (Node lNode) {
    this.lNode = lNode;
  }

  public Node intersection (Node otherNode) {
    Node interRNode = null;
    Node interLNode = null;

    if (this.getRNode() != null && otherNode.getRNode() != null) {
      interRNode = this.getRNode().intersection(otherNode.getRNode());
    }
    if (this.getLNode() != null && otherNode.getLNode() != null) {
      interLNode = this.getLNode().intersection(otherNode.getLNode());
    }

    return new Node(interRNode, interLNode);
  }

  public Node union (Node otherNode) {
    Node unionRNode = null;
    Node unionLNode = null;

    // if (this.getRNode() != null) {
    //   if (otherNode != null) {
    //     unionRNode = this.getRNode().union(otherNode.getRNode());
    //   } else {
    //     unionRNode = this.getRNode().union(null);
    //   }
    // } else if (otherNode != null && otherNode.getRNode() != null) {
    //   unionRNode = otherNode.getRNode().union(null);
    // }

    // if (this.getLNode() != null) {
    //   if (otherNode != null) {
    //     unionLNode = this.getLNode().union(otherNode.getLNode());
    //   } else {
    //     unionLNode = this.getLNode().union(null);
    //   }
    // } else if (otherNode != null && otherNode.getLNode() != null) {
    //   unionLNode = otherNode.getLNode().union(null);
    // }

    if (this != null && this.getRNode() != null) {
      Node otherRNode = (otherNode == null) ?
                            null : otherNode.getRNode();
      unionRNode = this.getRNode().union(otherRNode);
    } else if (otherNode != null && otherNode.getRNode() != null) {
      Node thisRNode = (this == null) ?
                       null : this.getRNode();
      unionRNode = otherNode.getRNode().union(thisRNode);
    }

    if (this != null && this.getLNode() != null) {
      Node otherLNode = (otherNode == null) ?
                        null : otherNode.getLNode();
      unionLNode = this.getLNode().union(otherLNode);
    } else if (otherNode != null && otherNode.getLNode() != null) {
      Node thisLNode = (this == null) ?
                       null : this.getLNode();
      unionLNode = otherNode.getLNode().union(thisLNode);
    }

    return new Node(unionRNode, unionLNode);
  }

  public String toString() {
    String rString = null;
    String lString = null;

    if (rNode == null) {
      rString = "";
    } else {
      rString = rNode.toString();
    }

    if (lNode == null) {
      lString = "";
    } else {
      lString = lNode.toString();
    }

    return "(" + lString + "," + rString + ")";
  }
}
