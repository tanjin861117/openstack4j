package org.openstack4j.openstack.networking.domain;

import java.util.Map;

import org.openstack4j.model.ModelEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("quota")
public class NeutronNetQuotaUsage implements ModelEntity {
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("subnet")
	private Map<String, Object> subnet;
    
    @JsonProperty("network")
	private Map<String, Object> network;
    
    @JsonProperty("floatingip")
	private Map<String, Object> floatingIp;
    
    @JsonProperty("security_group_rule")
	private Map<String, Object> securityGroupRule;
    
    @JsonProperty("security_group")
	private Map<String, Object> securityGroup;
    
    @JsonProperty("router")
	private Map<String, Object> router;
    
    @JsonProperty("port")
	private Map<String, Object> port;

	public Map<String, Object> getSubnet() {
		return subnet;
	}

	public void setSubnet(Map<String, Object> subnet) {
		this.subnet = subnet;
	}

	public Map<String, Object> getNetwork() {
		return network;
	}

	public void setNetwork(Map<String, Object> network) {
		this.network = network;
	}

	public Map<String, Object> getFloatingIp() {
		return floatingIp;
	}

	public void setFloatingIp(Map<String, Object> floatingIp) {
		this.floatingIp = floatingIp;
	}

	public Map<String, Object> getSecurityGroupRule() {
		return securityGroupRule;
	}

	public void setSecurityGroupRule(Map<String, Object> securityGroupRule) {
		this.securityGroupRule = securityGroupRule;
	}

	public Map<String, Object> getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(Map<String, Object> securityGroup) {
		this.securityGroup = securityGroup;
	}

	public Map<String, Object> getRouter() {
		return router;
	}

	public void setRouter(Map<String, Object> router) {
		this.router = router;
	}

	public Map<String, Object> getPort() {
		return port;
	}

	public void setPort(Map<String, Object> port) {
		this.port = port;
	}
    
    
}
