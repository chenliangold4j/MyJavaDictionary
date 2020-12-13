#!/bin/bash
./kube-scheduler \
  --leader-elect \
  --log-dir /opt/kubernetes/logs \
  --master http://127.0.0.1:8080 \
  --v 2