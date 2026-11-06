# Raft Integrated Storage Engine
storage "raft" {
  path    = "/vault/data"
  node_id = "vault-node-1"
}

# Production TLS Listener
listener "tcp" {
  address       = "0.0.0.0:8200"
  tls_cert_file = "/vault/certs/vault-cert.pem"
  tls_key_file  = "/vault/certs/vault-key.pem"
  tls_min_version = "tls12"
}

# Cluster Address for Raft replication
cluster_addr = "https://127.0.0.1:8201"
api_addr     = "https://vault.internal.yourdomain.com:8200"

# Security & Locks
disable_mlock = false # Locks memory to prevent paging keys to swap
ui            = true