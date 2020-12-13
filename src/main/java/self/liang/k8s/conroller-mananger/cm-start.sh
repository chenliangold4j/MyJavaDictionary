#!/bin/bash
./kube-controller-manager  \
--v 2 \
--log-dir /opt/kubernetes/logs \
--leader-elect true \
--master 127.0.0.1:8080 \
--cluster-cidr 172.7.0.0/16 \
--service-cluster-ip-range 192.168.0.0/16
