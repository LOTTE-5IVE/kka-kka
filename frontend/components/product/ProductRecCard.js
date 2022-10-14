import Link from "next/link";
import ProductInfo from "./ProductInfo";
import ProductRec from "./ProductRec";

export default function ProductRecCard({ id, imgsrc, name, price, discount }) {
  return (
    <>
      <ProductRec id={id} imgsrc={imgsrc} />
      <ProductInfo id={id} name={name} price={price} discount={discount} />
    </>
  );
}

ProductRecCard.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
  name: "test",
  price: 10000,
  rate: 10,
};
