package org.folio.okapi.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description of a module. These are used when creating modules under
 * "/_/proxy/modules" etc.
 *
 */
@JsonInclude(Include.NON_NULL)
public class ModuleDescriptor {

  private String id;
  private String name;

  private String[] tags;
  private EnvEntry[] env;

  private ModuleInterface[] requires;
  private ModuleInterface[] provides;
  private RoutingEntry[] routingEntries;
  private String[] modulePermissions;
  private UiModuleDescriptor uiDescriptor;
  private LaunchDescriptor launchDescriptor;

  private String tenantInterface;

  public ModuleDescriptor() {
  }

  /**
   * Copy constructor.
   *
   * @param other
   */
  public ModuleDescriptor(ModuleDescriptor other) {
    this.id = other.id;
    this.name = other.name;
    this.tags = other.tags;
    this.env = other.env;
    this.routingEntries = other.routingEntries;
    this.requires = other.requires;
    this.provides = other.provides;
    this.modulePermissions = other.modulePermissions;
    this.uiDescriptor = other.uiDescriptor;
    this.launchDescriptor = other.launchDescriptor;
    this.tenantInterface = other.tenantInterface;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonIgnore
  public String getNameOrId() {
    if (name != null && !name.isEmpty()) {
      return name;
    }
    return id;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public EnvEntry[] getEnv() {
    return env;
  }

  public void setEnv(EnvEntry[] env) {
    this.env = env;
  }

  public ModuleInterface[] getRequires() {
    return requires;
  }

  public void setRequires(ModuleInterface[] requires) {
    this.requires = requires;
  }

  public ModuleInterface[] getProvides() {
    return provides;
  }

  public void setProvides(ModuleInterface[] provides) {
    this.provides = provides;
  }

  public RoutingEntry[] getRoutingEntries() {
    return routingEntries;
  }

  public void setRoutingEntries(RoutingEntry[] routingEntries) {
    this.routingEntries = routingEntries;
  }

  /**
   * Get all RoutingEntries that are type proxy
   *
   * @param interfaceType - "system" or "proxy"
   * @return
   */
  @JsonIgnore
  public List<RoutingEntry> getProxyRoutingEntries() {
    List<RoutingEntry> all = new ArrayList<>();
    RoutingEntry[] res = getRoutingEntries();
    if (res != null) {
      Collections.addAll(all, res);
    }
    ModuleInterface[] prov = getProvides();
    if (prov != null) {
      for (ModuleInterface mi : prov) {
        String t = mi.getInterfaceType();
        if (t == null || t.isEmpty()) {
          t = "proxy";
        }
        if (t.equals("proxy")) {
          res = mi.getRoutingEntries();
          if (res != null) {
            Collections.addAll(all, res);
          }
        }
      }
    }
    return all;
  }

  public String[] getModulePermissions() {
    return modulePermissions;
  }

  public void setModulePermissions(String[] modulePermissions) {
    this.modulePermissions = modulePermissions;
  }

  public UiModuleDescriptor getUiDescriptor() {
    return uiDescriptor;
  }

  public void setUiDescriptor(UiModuleDescriptor uiDescriptor) {
    this.uiDescriptor = uiDescriptor;
  }

  public LaunchDescriptor getLaunchDescriptor() {
    return launchDescriptor;
  }

  public void setLaunchDescriptor(LaunchDescriptor launchDescriptor) {
    this.launchDescriptor = launchDescriptor;
  }

  public String getTenantInterface() {
    return tenantInterface;
  }

  public void setTenantInterface(String tenantInterface) {
    this.tenantInterface = tenantInterface;
  }


}
