import Layout from "../components/common/Layout";
import "../styles/globals.scss";
import { QueryClientProvider, QueryClient } from "react-query";
import { Suspense } from "react";
import Header from "../components/common/HeaderComp";

const queryClient = new QueryClient();

function MyApp({ Component, pageProps }) {
  return (
    <>
      <QueryClientProvider client={queryClient}>
        {Component.name == "admin" || Component.name == "NotFound" ? (
          <Component {...pageProps} />
        ) : (
          <Layout>
            <Component {...pageProps} />
          </Layout>
        )}
      </QueryClientProvider>
    </>
  );
}

export default MyApp;
