provider "aws" {
  version = "~> 2"
  region  = var.region
}

provider "local" {
  version = "~> 1.2"
}

