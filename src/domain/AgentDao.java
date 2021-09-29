package domain;

import java.util.List;

public interface AgentDao {
	public Agent findAgent(String agentId);
	
	public List<Agent> getAllAgents();
	
	public void readAgentFile();
}
