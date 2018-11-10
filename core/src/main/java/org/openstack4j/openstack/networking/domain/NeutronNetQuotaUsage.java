package org.openstack4j.openstack.networking.domain;

import java.util.Map;

import org.openstack4j.model.ModelEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("quota")
public class NeutronNetQuotaUsage implements ModelEntity {
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("subnet")
	private Map<String, Integer> subnet;
    
    @JsonProperty("network")
	private Map<String, Integer> network;
    
    @JsonProperty("floatingip")
	private Map<String, Integer> floatingIp;
    
    @JsonProperty("security_group_rule")
	private Map<String, Integer> securityGroupRule;
    
    @JsonProperty("security_group")
	private Map<String, Integer> securityGroup;
    
    @JsonProperty("router")
	private Map<String, Integer> router;
    
    @JsonProperty("port")
	private Map<String, Integer> port;

	public Map<String, Integer> getSubnet() {
		return subnet;
	}

	public void setSubnet(Map<String, Integer> subnet) {
		this.subnet = subnet;
	}

	public Map<String, Integer> getNetwork() {
		return network;
	}

	public void setNetwork(Map<String, Integer> network) {
		this.network = network;
	}

	public Map<String, Integer> getFloatingIp() {
		return floatingIp;
	}

	public void setFloatingIp(Map<String, Integer> floatingIp) {
		this.floatingIp = floatingIp;
	}

	public Map<String, Integer> getSecurityGroupRule() {
		return securityGroupRule;
	}

	public void setSecurityGroupRule(Map<String, Integer> securityGroupRule) {
		this.securityGroupRule = securityGroupRule;
	}

	public Map<String, Integer> getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(Map<String, Integer> securityGroup) {
		this.securityGroup = securityGroup;
	}

	public Map<String, Integer> getRouter() {
		return router;
	}

	public void setRouter(Map<String, Integer> router) {
		this.router = router;
	}

	public Map<String, Integer> getPort() {
		return port;
	}

	public void setPort(Map<String, Integer> port) {
		this.port = port;
	}
    
    
}
