import { useEffect, useState } from "react";

export default function ValidDate({ setStartDate, setEndDate }) {
  let curr = new Date();
  curr.setDate(curr.getDate());
  const date = curr.toISOString().substring(0, 10);
  const [duration, setDuration] = useState(0);

  useEffect(() => {
    setStartDate(date);
  }, [duration]);

  return (
    <div className="dateWrapper" style={{ display: "flex" }}>
      <div>
        발급일로부터{" "}
        <input
          type="text"
          size="1"
          defaultValue={duration}
          onChange={(e) => {
            let now = new Date();
            let dur = new Date(
              now.setDate(now.getDate() + Number(e.target.value)),
            );
            setDuration(e.target.value);
            setEndDate(dur.toISOString().substring(0, 10));
          }}
        />{" "}
        일 유효
      </div>
    </div>
  );
}
