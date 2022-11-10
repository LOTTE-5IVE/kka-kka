import Layout from "../components/common/Layout";
import "../styles/globals.scss";
import { QueryClientProvider, QueryClient } from "react-query";
import { TokenContext } from "../context/TokenContext";
import { useState } from "react";
import { CartCntContext } from "../context/CartCntContext";

const queryClient = new QueryClient();

function MyApp({ Component, pageProps }) {
  const [token, setToken] = useState();
  const [cartCnt, setCartCnt] = useState(0);

  return (
    <>
      <QueryClientProvider client={queryClient}>
        {Component.displayName === "Admin" ||
        Component.displayName == "NotFound" ? (
          <Component {...pageProps} />
        ) : (
          <TokenContext.Provider value={{ token, setToken }}>
            <CartCntContext.Provider value={{ cartCnt, setCartCnt }}>
              <Layout>
                <Component {...pageProps} />
              </Layout>
            </CartCntContext.Provider>
          </TokenContext.Provider>
        )}
      </QueryClientProvider>
    </>
  );
}

export default MyApp;
