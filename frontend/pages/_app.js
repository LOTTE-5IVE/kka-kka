import Layout from "../components/common/Layout";
import "../styles/globals.scss";
import {QueryClient, QueryClientProvider} from "react-query";
import {TokenContext} from "../context/TokenContext";
import {useState} from "react";
import {CartCntContext} from "../context/CartCntContext";
import {PaymentContext} from "../context/PaymentContext";
import {AdminProvider} from "../context/AdminTokenContext";

const queryClient = new QueryClient();

function MyApp({Component, pageProps}) {
  const [token, setToken] = useState();
  const [cartCnt, setCartCnt] = useState(0);
  const [payment, setPayment] = useState();

  return (
    <>
      <QueryClientProvider client={queryClient}>
        {Component.displayName === "Admin"
        || Component.displayName === "NotFound"
        || Component.displayName === "AdminLogin" ? (
          <AdminProvider>
            <Component {...pageProps} />
          </AdminProvider>
        ) : (
          <TokenContext.Provider value={{ token, setToken }}>
            <CartCntContext.Provider value={{ cartCnt, setCartCnt }}>
              <PaymentContext.Provider value={{ payment, setPayment }}>
                <Layout>
                  <Component {...pageProps} />
                </Layout>
              </PaymentContext.Provider>
            </CartCntContext.Provider>
          </TokenContext.Provider>
        )}
      </QueryClientProvider>
    </>
  );
}

export default MyApp;
