/** @type {import('next').NextConfig} */
const path = require("path");

const nextConfig = {
  reactStrictMode: false,
  swcMinify: true,
  sassOptions: {
    includePaths: [path.join(__dirname, "styles")],
  },
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: `http://localhost:9000/api/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
