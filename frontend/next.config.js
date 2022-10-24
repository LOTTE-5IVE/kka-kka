/** @type {import('next').NextConfig} */
const path = require("path");

const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  sassOptions: {
    includePaths: [path.join(__dirname, "styles")],
  },
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: `https://24fe-14-32-196-148.jp.ngrok.io/api/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
