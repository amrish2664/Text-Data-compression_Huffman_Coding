import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
class Node implements Comparable<Node> {
    char ch;
    int frequency;
    Node left, right;

    Node(char ch, int frequency) {
        this.ch = ch;
        this.frequency = frequency;
        left = right = null;
    }

    Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node node) {
        return this.frequency - node.frequency;
    }
}

class HuffmanCoding {

    // Build frequency map
    public static Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }
        return frequencyMap;
    }

    // Build Huffman Tree
    public static Node buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node merged = new Node(left.frequency + right.frequency, left, right);
            priorityQueue.add(merged);
        }

        return priorityQueue.poll(); // Root node
    }

    // Generate Huffman Codes
    public static void generateCodes(Node root, String code, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            huffmanCode.put(root.ch, code);
        }

        generateCodes(root.left, code + "0", huffmanCode);
        generateCodes(root.right, code + "1", huffmanCode);
    }

    // Encode the text
    public static String encodeText(String text, Map<Character, String> huffmanCode) {
        StringBuilder encodedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedText.append(huffmanCode.get(ch));
        }
        return encodedText.toString();
    }

    // Decode the text
    public static String decodeText(String encodedText, Node root) {
        StringBuilder decodedText = new StringBuilder();
        Node currentNode = root;

        for (int i = 0; i < encodedText.length(); i++) {
            currentNode = encodedText.charAt(i) == '0' ? currentNode.left : currentNode.right;

            if (currentNode.left == null && currentNode.right == null) {
                decodedText.append(currentNode.ch);
                currentNode = root;
            }
        }
        return decodedText.toString();
    }

    // Main method to test the Huffman Coding implementation
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String text=sc.nextLine();
        

        // Build frequency map
        Map<Character, Integer> frequencyMap = buildFrequencyMap(text);

        // Print the frequency map
        System.out.println("Character Frequency Map: " + frequencyMap);

        // Build Huffman Tree
        Node root = buildHuffmanTree(frequencyMap);

        // Generate Huffman Codes
        Map<Character, String> huffmanCode = new HashMap<>();
        generateCodes(root, "", huffmanCode);

        System.out.println("Huffman Codes: " + huffmanCode);

        // Encode text
        String encodedText = encodeText(text, huffmanCode);
        System.out.println("Encoded Text: " + encodedText);

        // Decode text
        String decodedText = decodeText(encodedText, root);
        System.out.println("Decoded Text: " + decodedText);
    }
}
