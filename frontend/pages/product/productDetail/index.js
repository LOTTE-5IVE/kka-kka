import axios from "axios";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { PostHApi } from "../../../apis/Apis";
import Title from "../../../components/common/Title";
import { useGetToken } from "../../../hooks/useGetToken";
import ProductDetailLayout from "../../../layouts/ProductDetailLayout";
import Swal from "sweetalert2";

export default function productDetail() {
  const router = useRouter();
  const productId = router.query.id;
  const [token, setToken] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [tab, setTab] = useState("info");
  const [modal, setModal] = useState(false);
  const [product, setProduct] = useState({});
  const [reviews, setReviews] = useState();

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

  const addCartItem = async (product, quantity) => {
    setToken(useGetToken());

    Swal.fire({
      title: "장바구니에 담으시겠습니까?",
      html: `${product.name}` + "<br/>" + `수량 : ${quantity}개`,
      imageUrl: `${product.image_url}`,
      imageHeight: 300,
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "장바구니 담기",
      cancelButtonText: "취소",
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire("", "장바구니에 성공적으로 담겼습니다.", "success");
      }
    });

    PostHApi(
      "/api/carts",
      {
        productId: product.id,
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

  const getReview = async () => {
    if (productId) {
      await axios.get(`/api/reviews?product=4330`).then((res) => {
        setReviews(res.data);
      });
    }
  };

  useEffect(() => {
    if (!router.isReady) {
      return;
    }

    getReview();
  }, [router.isReady, token, quantity]);

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
        reviews={reviews}
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
