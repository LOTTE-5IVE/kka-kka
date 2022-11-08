import Layout from "../components/common/Layout";
import "../styles/globals.scss";
import { QueryClientProvider, QueryClient } from "react-query";
import { TokenContext } from "../context/TokenContext";
import { useState } from "react";

const queryClient = new QueryClient();

function MyApp({ Component, pageProps }) {
  const [token, setToken] = useState();

  return (
    <>
      <QueryClientProvider client={queryClient}>
        {Component.name == "Admin" || Component.name == "NotFound" ? (
          <Component {...pageProps} />
        ) : (
          <TokenContext.Provider value={{ token, setToken }}>
            <Layout>
              <Component {...pageProps} />
            </Layout>
          </TokenContext.Provider>
        )}
      </QueryClientProvider>
    </>
  );
}

export default MyApp;
