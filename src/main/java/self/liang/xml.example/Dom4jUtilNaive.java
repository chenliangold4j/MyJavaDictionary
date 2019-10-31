package self.liang.xml.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtilNaive 
{
	public static  Document parse(File file)throws DocumentException
	{
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document;
	}
	
	public static void bar(Document document) throws DocumentException{
		Element root = document.getRootElement();
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
	        Element element = it.next();
	        System.out.println(element.getName());
	        System.out.println(element.getData());
	        // do something
	    }
		


	    // iterate through child elements of root with element name "foo"
	    for (Iterator<Element> it = root.elementIterator("to"); it.hasNext();) {
	        Element foo = it.next();
	        System.out.println(foo.getData());
	        // do something
	    }

	    // iterate through attributes of root
	    for (Iterator<Attribute> it = root.attributeIterator(); it.hasNext();) {
	        Attribute attribute = it.next();
	        System.out.println(attribute.getName());
	        System.out.println(attribute.getData());
	        // do something
	    }
	}
	
	
	public static void XpathBar(Document document) throws DocumentException{
//		第一种形式
//    　　　　/AAA/DDD/BBB： 表示一层一层的，AAA下面 DDD下面的BBB
//　  	第二种形式
//   　　　　 //BBB： 表示和这个名称相同，表示只要名称是BBB，都得到
//     	第三种形式
//    　　　　/*: 所有元素
//    	 第四种形式
//   　　　　 BBB[1]：　表示第一个BBB元素
//    　  　　BBB[last()]：表示最后一个BBB元素
//    	 第五种形式
//    　　　　//BBB[@id]： 表示只要BBB元素上面有id属性，都得到
//     	第六种形式
//    　　　　//BBB[@id='b1'] 表示元素名称是BBB,在BBB上面有id属性，并且id的属性值是b1
		List<Node> list = document.selectNodes("//note");
		Node node = document.selectSingleNode("//note/from");
		System.out.println(node.getName());
		System.out.println(list.get(0).valueOf("@id"));
		
	}
	
//	大文件
	public static void treeWalk(Document document) {
	    treeWalk(document.getRootElement());
	}

	public static void treeWalk(Element element) {
	    for (int i = 0, size = element.nodeCount(); i < size; i++) {
	        Node node = element.node(i);
	        if (node instanceof Element) {
	            treeWalk((Element) node);
	        }
	        else {
	            // do something…
	        }
	    }
	}
	
	
	public static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        Element author1 = root.addElement("author")
            .addAttribute("name", "James")
            .addAttribute("location", "UK")
            .addText("James Strachan");

        Element author2 = root.addElement("author")
            .addAttribute("name", "Bob")
            .addAttribute("location", "US")
            .addText("Bob McWhirter");

        return document;
    }
	
	public static void write(Document document) throws IOException {

        // lets write to a file
        FileWriter fileWiter = new FileWriter("D:/test/output.xml"); 
        XMLWriter writer = new XMLWriter(fileWiter);
        writer.write( document );
        writer.close();
       

        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        writer.write( document );

        // Compact format to System.out
        format = OutputFormat.createCompactFormat();
        writer = new XMLWriter(System.out, format);
        writer.write(document);
        writer.close();
    }
	
	public Document styleDocument(Document document, String stylesheet) throws Exception {

        // load the transformer using JAXP
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));

        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);

        // return the transformed document
        Document transformedDoc = result.getDocument();
        return transformedDoc;
    }
	
	public static void main(String[] args) throws DocumentException, IOException {
//		bar(parse(new File("D:/test/1.txt")));
//		XpathBar(parse(new File("D:/test/1.txt")));
		Document document = createDocument();
//		FileWriter out = new FileWriter("D:/test/2.txt");
//		document.write(out);
//		out.close();
//		write(document);
		
		String text = document.asXML();;
		System.out.println(text);
		String text2 = "<person> <name>James</name> </person>";
		Document document2 = DocumentHelper.parseText(text);
		
	}

	
}
