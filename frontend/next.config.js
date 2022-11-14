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

  images: {
    domains: ["d1n7ys0in64qon.cloudfront.net"],
  },
};

module.exports = nextConfig;
