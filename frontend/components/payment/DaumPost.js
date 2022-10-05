import REACT, { useEffect, useState } from "react";
import DaumPostCode from "react-daum-postcode";

export default function DaumPost({ zipcodeHandler, addrHandler }) {
  const [zipcode, setZipcode] = useState("");
  const [addr1, setAddr1] = useState("");

  useEffect(() => {
    zipcodeHandler(zipcode);
    addrHandler(addr1);
  });

  const handleComplete = (data) => {
    let fullAddress = data.address;
    let extraAddress = "";
    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
      setZipcode(data.zonecode);
      setAddr1(fullAddress);
    }
    //fullAddress -> 전체 주소반환
  };
  return (
    <div>
      <DaumPostCode
        onComplete={handleComplete}
        className="post-code"
        autoClose
      />
    </div>
  );
}
