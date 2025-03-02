package szczur4.paint;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.awt.*;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.border.*;

import static szczur4.paint.paint.*;
public class configReader{
	configReader()throws Exception{
		Scanner sc=new Scanner(Objects.requireNonNull(paint.class.getResource("config.json5")).openStream());
		String json=sc.nextLine();
		while(sc.hasNextLine())json=json.concat(sc.nextLine());
		json=json.replaceAll("#","");
		JsonMapper mapper=JsonMapper.builder().enable(JsonReadFeature.ALLOW_JAVA_COMMENTS).build();
		JsonNode node=mapper.readTree(json);
		String theme=node.get("theme").asText();
		node=node.get("themes").get(theme);
		back=new Color(Integer.parseInt(node.get("back").asText(),16));
		medium=new Color(Integer.parseInt(node.get("medium").asText(),16));
		fore=new Color(Integer.parseInt(node.get("fore").asText(),16));
		border=new LineBorder(fore,1);
	}
}
