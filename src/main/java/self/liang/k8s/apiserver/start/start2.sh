#!/bin/bash
./kube-apiserver \
  --apiserver-count 2 \
  --audit-log-path /opt/kubernetes/k8s-audit.log \
  --audit-policy-file ./conf/audit.yaml \
  --log-dir /opt/kubernetes/logs \
  --authorization-mode RBAC \
  --enable-admission-plugins NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ResourceQuota \
  --etcd-servers http://172.26.216.158:2379,http://172.26.216.159:2379,http://172.26.216.160:2379 \
  --allow-privileged true \
  --service-cluster-ip-range 192.168.0.0/16 \
  --service-node-port-range 30000-32767 \
  --target-ram-mb=1024 \
  --v 2
