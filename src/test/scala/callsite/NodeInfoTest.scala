package callsite

import org.scalatest.{FunSuiteLike, Matchers}

class NodeInfoTest extends FunSuiteLike with Matchers {
  test("all host address (ipv4) should be xxx.xxx.xxx.xxx where xxx between [0;255]") {
    val nodeInfo = NodeInfo.nodeInfo
    val result = nodeInfo.nodeInfoNetworkInterfaceList
      .map(x ⇒
        x.hostAddress.matches(
          "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$"))
    result should contain only true
  }

  test("validate the returned ipv6 addresses") {
    val nodeInfo = NodeInfo.nodeInfo
    val result = nodeInfo.nodeInfoNetworkInterfaceList
      .filter(x ⇒ x.hostIpv6Address.isDefined)
      .map(x ⇒
        x.hostIpv6Address.get.matches(
          "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))"))
    result should contain only true
  }

  test("validate the returned hardware addresses") {
    val nodeInfo = NodeInfo.nodeInfo
    val result = nodeInfo.nodeInfoNetworkInterfaceList
      .filter(x ⇒ x.hardwareAddress.isDefined)
      .map(x ⇒ x.hardwareAddress.get.matches("^[a-fA-F0-9:]{17}|[a-fA-F0-9]{12}$"))
    result should contain only true
  }
}
