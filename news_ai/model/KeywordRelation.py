from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional

@dataclass
class KeywordRelation:
    keyword_id : int
    related_keyword_id : int
    similarity : float
    id: Optional[int] = field(default=None)