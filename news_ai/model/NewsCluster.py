from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional

@dataclass
class NewsCluster:
    cluster_id: str
    created_date: datetime
    news_article_id : int
    cluster_title : str
    id: Optional[int] = field(default=None)