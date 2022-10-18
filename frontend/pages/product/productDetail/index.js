import axios from "axios";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { PostHApi } from "../../../apis/Apis";
import Title from "../../../components/common/Title";
import { useGetToken } from "../../../hooks/useGetToken";
import ProductDetailLayout from "../../../layouts/ProductDetailLayout";

export default function productDetail() {
  const router = useRouter();
  const productId = router.query.id;
  const [token, setToken] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [tab, setTab] = useState("info");
  const [modal, setModal] = useState(false);
  const [product, setProduct] = useState({});

  const handleQuantity = (type) => {
    if (type !== "minus" || quantity > 1) {
      type === "plus" ? setQuantity(quantity + 1) : setQuantity(quantity - 1);
    }
  };

  function handleModal(bool) {
    setModal(bool);
  }

  function handleTab(text) {
    setTab(text);
  }

  const addCartItem = async (id, quantity) => {
    PostHApi(
      "/api/carts",
      {
        productId: id,
        quantity: quantity,
      },
      token,
    );
  };

  const getItem = async () => {
    if (productId) {
      await axios.get(`/api/products/${productId}`).then((res) => {
        setProduct(res.data);
      });
    }
  };

  useEffect(() => {
    setToken(useGetToken());
  }, [token, quantity]);

  useState(() => {
    getItem();
  }, [productId]);

  return (
    <>
      <Title title="상품상세" />
      <ProductDetailLayout
        tab={tab}
        modal={modal}
        product={product}
        quantity={quantity}
        handleModal={handleModal}
        handleTab={handleTab}
        handleQuantity={handleQuantity}
        addCartItem={addCartItem}
      />
    </>
  );
}

export async function getServerSideProps(context) {
  return {
    props: {},
  };
}
