package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AgentDaoImpl implements AgentDao {
	private List<Agent> agentList;
	
	public AgentDaoImpl() {
		this.agentList = new ArrayList<>();
	}

	@Override
	public Agent findAgent(String agentId) {
		Agent selectedAgent = null;
		
		for (Agent agent : agentList) {
			if (agent.getId().equals(agentId))
				selectedAgent = agent;
		}
		
		return selectedAgent;
	}

	@Override
	public List<Agent> getAllAgents() {
		return agentList;
	}

	@Override
	public void readAgentFile() {
		ArrayList<String[]> linesRead = new ArrayList<String[]>();
		String fileName = "agents.txt";
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new File(fileName));
		
			while (inputStream.hasNextLine()) {
				String singleLine = inputStream.nextLine();
				String[] tokens = singleLine.split(",");
				linesRead.add(tokens);
			}
			
			for (String[] strArray : linesRead) {
				Agent agent = new Agent(strArray[0], strArray[1], strArray[2], strArray[3], strArray[4]);
				
				agentList.add(agent);
			}
		
		} catch (FileNotFoundException exc) {
			System.out.println("\"" + fileName + "\" not found.");
		}
		
		inputStream.close();
	}
}
