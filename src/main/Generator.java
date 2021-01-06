package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Generator
{

	/**
	 * Generate the file result
	 * 
	 * @param modelFile
	 * @param blockAttributs
	 * @throws IOException
	 */
    public static void generate(String modelFile, String blockAttributs) throws IOException
    {
        String modele = new String(Files.readAllBytes(Paths.get("resources/models/" + modelFile)), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
    	if(blockAttributs == null || blockAttributs.length() == 0) {
    		sb.append(modele);
        } else {
	        String[] attributs = blockAttributs.split("\\n");
	        for(int i = 0; i < attributs.length; i++)
	        {
	            String text = new String(modele);
	            String[] params = attributs[i].split("\\t");
	            if(text.contains("@{FIELD}"))
	            {
	                text = text.replace("@{FIELD}", "@{FIELD_1}");
	            }
	            for(int j = 0; j < params.length; j++)
	            {
	                if(text.contains("@{FIELD_" + (j + 1) + "}"))
	                {
	                    text = text.replace("@{FIELD_" + (j + 1) + "}", params[j]);
	                }
	            }
	            if(text.contains("@{INDEX}"))
	            {
	                text = text.replace("@{INDEX}", String.valueOf(i + 1));
	            }
	            sb.append(text + "\n");
	        }
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resources/result.txt"), StandardCharsets.UTF_8))) {
            bw.write(sb.toString());
        }
    }

}
