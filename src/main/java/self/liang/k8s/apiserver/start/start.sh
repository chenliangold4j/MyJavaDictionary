#!/bin/bash
./kube-apiserver \
  --apiserver-count 2 \
  --audit-log-path /opt/kubernetes/k8s-audit.log \
  --audit-policy-file ./conf/audit.yaml \
  --log-dir /opt/kubernetes/logs \
  --authorization-mode RBAC \
  --enable-admission-plugins NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ResourceQuota \
#  --client-ca-file /opt/ssl/k8sca/ca.pem
  # etcd的地址
  --etcd-servers http://172.26.216.158:2379,http://172.26.216.159:2379,http://172.26.216.160:2379 \
#  --etcd-cafile /opt/ssl/k8sca/ca.pem
#  --etcd-certfile /opt/ssl/k8sca/etcd.pem
#  --etcd-keyfile /opt/ssl/k8sca/etcd-key.pem
  # 本服务，APIServer的地址
#  --bind-address 192.168.111.119
  # 本服务端口
#  --secure-port 6443
  # 广播地址
#  --advertise-address 192.168.111.119
  # 是否 使用超级管理员权限创建容器
  --allow-privileged true \
  # 启动Service时生成的虚拟网段
  --service-cluster-ip-range 192.168.0.0/16 \
  # service端口范围
  --service-node-port-range 30000-32767 \
  # 开启插件
  --target-ram-mo=1024 \
  # 授权模式

  # 实现基于token自动颁发证书
#  --enable-bootstrap-token-auth true
  # 颁发证书的token
#  --token-auth-file /opt/kubernetes/cfg/token.csv
#  --kubelet-client-certificate /opt/ssl/k8sca/etcd.pem
#  --kubelet-client-key /opt/ssl/k8sca/etcd-key.pem
#  --tls-cert-file /opt/ssl/k8sca/etcd.pem
#  --tls-private-key-file /opt/ssl/k8sca/etcd-key.pem
#  --service-account-key-file /opt/ssl/k8sca/ca-key.pem
  # 日志级别
  --v 2
