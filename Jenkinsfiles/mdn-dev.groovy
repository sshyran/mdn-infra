stage('virginia') {
  sh 'kubectl config use-context virginia'
  env.GIT_COMMIT_SHORT = 'a3a53b7'
  env.MEMCACHED_RELEASE = 'mdn-dev'
  env.MYSQL_RELEASE = 'manageable-puffin'
  //TODO use j2 from docker to eliminate system dependency
  sh 'j2 mdn/k8s/mdn-dev.yaml.jinja | kubectl apply -n mdn-dev -f -'
}

stage('tokyo') {
  sh 'kubectl config use-context tokyo'
  env.NAMESPACE = env.BRANCH_NAME
  env.MYSQL_RELEASE = env.BRANCH_NAME
  env.MEMCACHED_RELEASE = env.BRANCH_NAME
  env.GIT_COMMIT_SHORT = 'a3a53b7'
  //TODO use j2 from docker to eliminate system dependency
  sh 'j2 mdn/k8s/mdn-dev.yaml.jinja | kubectl apply -n mdn-dev -f -'
  sh 'helm install --namespace $NAMESPACE --name $MEMCACHED_RELEASE --reuse-name stable/memcached'
  //sh 'helm install --namespace $NAMESPACE --name $MYSQL_RELEASE --reuse-name --set persistence.size=40Gi stable/mysql '
}
